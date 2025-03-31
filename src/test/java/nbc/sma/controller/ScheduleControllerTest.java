package nbc.sma.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import nbc.sma.dto.request.CreateScheduleRequest;
import nbc.sma.dto.request.EditScheduleRequest;
import nbc.sma.dto.request.ScheduleSearchRequest;
import nbc.sma.dto.response.FindSchedulesResponse;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.service.ScheduleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ScheduleController.class)
class ScheduleControllerTest extends RestDocsTestSupport {

    @MockitoBean
    private ScheduleService scheduleService;

    @Test
    @DisplayName("일정 생성 성공")
    void createSchedule_success() throws Exception {

        // given
        CreateScheduleRequest req = new CreateScheduleRequest(1L, "비밀번호", "할 일");
        ScheduleResponse res =
                new ScheduleResponse(1L, new UserResponse(1L, "test@test.com", "test"), "할 일", LocalDateTime.now(), LocalDateTime.now());

        when(scheduleService.createSchedule(req)).thenReturn(res);

        // when
        ResultActions resultActions = mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        // then
        resultActions
                .andExpect(status().isCreated());

        // rest docs
        resultActions
                .andDo(document("{class-name}/{method-name}",
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("일정 관리")
                                        .description("일정 등록")
                                        .requestFields(
                                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                                fieldWithPath("task").type(JsonFieldType.STRING).description("할 일")
                                        )
                                        .requestSchema(Schema.schema("CreateScheduleRequest"))
                                        .responseFields(
                                                fieldWithPath("scheduleId").type(JsonFieldType.NUMBER).description("일정 ID"),
                                                fieldWithPath("user").type(JsonFieldType.OBJECT).description("작성자 정보"),
                                                fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                                fieldWithPath("user.email").type(JsonFieldType.STRING).description("작성자 이메일"),
                                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("작성자 이름"),
                                                fieldWithPath("task").type(JsonFieldType.STRING).description("할 일"),
                                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("등록일"),
                                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일")
                                        )
                                        .responseSchema(Schema.schema("ScheduleResponse"))
                                        .build()
                        )));
    }

    @Test
    @DisplayName("전체 일정 조회 성공")
    void findSchedules_success() throws Exception {

        // given
        ScheduleSearchRequest cond = new ScheduleSearchRequest(LocalDate.now(), "이름", 1L, 1, 15);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("updatedAt", cond.getUpdatedAt().toString());
        params.add("username", cond.getUsername());
        params.add("userId", cond.getUserId().toString());
        params.add("page", cond.getPage().toString());
        params.add("size", cond.getSize().toString());

        FindSchedulesResponse res =
                new FindSchedulesResponse(
                        List.of(new ScheduleResponse(1L, new UserResponse(1L, "test@test.com", "test"), "할 일", LocalDateTime.now(), LocalDateTime.now())),
                        1,
                        1
                );

        when(scheduleService.findSchedules(cond)).thenReturn(res);

        // when
        ResultActions resultActions = mockMvc.perform(get("/schedules")
                .queryParams(params));

        // then
        resultActions
                .andExpect(status().isOk());

        // rest docs
        resultActions
                .andDo(document("{class-name}/{method-name}",
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("일정 관리")
                                        .description("전체 일정 조회")
                                        .queryParameters(
                                                parameterWithName("updatedAt").description("수정일").type(SimpleType.STRING).optional(),
                                                parameterWithName("username").description("작성자 이름").type(SimpleType.STRING).optional(),
                                                parameterWithName("userId").description("작성자 ID").type(SimpleType.NUMBER).optional(),
                                                parameterWithName("page").description("페이지").type(SimpleType.NUMBER).optional(),
                                                parameterWithName("size").description("일정 수").type(SimpleType.NUMBER).optional()
                                        )
                                        .responseFields(
                                                fieldWithPath("results").type(JsonFieldType.ARRAY).description("일정 목록"),
                                                fieldWithPath("results[].scheduleId").type(JsonFieldType.NUMBER).description("일정 ID"),
                                                fieldWithPath("results[].user").type(JsonFieldType.OBJECT).description("작성자 정보"),
                                                fieldWithPath("results[].user.id").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                                fieldWithPath("results[].user.email").type(JsonFieldType.STRING).description("작성자 이메일"),
                                                fieldWithPath("results[].user.name").type(JsonFieldType.STRING).description("작성자 이름"),
                                                fieldWithPath("results[].task").type(JsonFieldType.STRING).description("할 일"),
                                                fieldWithPath("results[].createdAt").type(JsonFieldType.STRING).description("등록일"),
                                                fieldWithPath("results[].updatedAt").type(JsonFieldType.STRING).description("수정일"),
                                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("일정 수"),
                                                fieldWithPath("curPage").type(JsonFieldType.NUMBER).description("현재 페이지")
                                        )
                                        .responseSchema(Schema.schema("FindSchedulesResponse"))
                                        .build()
                        )));
    }

    @Test
    @DisplayName("일정 조회 성공")
    void findSchedule_success() throws Exception {

        // given
        Long scheduleId = 1L;
        ScheduleResponse res =
                new ScheduleResponse(scheduleId, new UserResponse(1L, "test@test.com", "test"), "할 일", LocalDateTime.now(), LocalDateTime.now());

        when(scheduleService.findSchedule(scheduleId)).thenReturn(res);


        // when
        ResultActions resultActions = mockMvc
                .perform(get("/schedules/{scheduleId}", scheduleId));

        // then
        resultActions
                .andExpect(status().isOk());

        // rest docs
        resultActions
                .andDo(document("{class-name}/{method-name}",
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("일정 관리")
                                        .description("일정 조회")
                                        .pathParameters(
                                                parameterWithName("scheduleId").description("일정 ID").type(SimpleType.NUMBER)
                                        )
                                        .responseFields(
                                                fieldWithPath("scheduleId").type(JsonFieldType.NUMBER).description("일정 ID"),
                                                fieldWithPath("user").type(JsonFieldType.OBJECT).description("작성자 정보"),
                                                fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                                fieldWithPath("user.email").type(JsonFieldType.STRING).description("작성자 이메일"),
                                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("작성자 이름"),
                                                fieldWithPath("task").type(JsonFieldType.STRING).description("할 일"),
                                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("등록일"),
                                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일")
                                        )
                                        .responseSchema(Schema.schema("ScheduleResponse"))
                                        .build()
                        )));
    }

    @Test
    @DisplayName("일정 수정 성공")
    void editSchedule_success() throws Exception {

        // given
        Long scheduleId = 1L;
        EditScheduleRequest req = new EditScheduleRequest("test", "할 일", "비밀번호");

        // when
        ResultActions resultActions = mockMvc
                .perform(put("/schedules/{scheduleId}/edit", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req))
                );

        // then
        resultActions
                .andExpect(status().isOk());

        // rest docs
        resultActions
                .andDo(document("{class-name}/{method-name}",
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("일정 관리")
                                        .description("일정 수정")
                                        .requestFields(
                                                fieldWithPath("username").type(JsonFieldType.STRING).description("작성자 이름"),
                                                fieldWithPath("task").type(JsonFieldType.STRING).description("할 일"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                        )
                                        .requestSchema(Schema.schema("EditScheduleRequest"))
                                        .build()
                        )));
    }

    @Test
    @DisplayName("일정 삭제 성공")
    void deleteSchedule_success() throws Exception {

        // given
        Long scheduleId = 1L;

        // when
        ResultActions resultActions = mockMvc
                .perform(delete("/schedules/{scheduleId}", scheduleId));

        // then
        resultActions
                .andExpect(status().isOk());

        // rest docs
        resultActions
                .andDo(document("{class-name}/{method-name}",
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("일정 관리")
                                        .description("일정 삭제")
                                        .pathParameters(
                                                parameterWithName("scheduleId").description("일정 ID").type(SimpleType.NUMBER)
                                        )
                                        .build()
                        )));
    }
}