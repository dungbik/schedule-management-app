package nbc.sma.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import nbc.sma.controller.request.RegisterRequest;
import nbc.sma.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends RestDocsTestSupport {

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("사용자 등록 성공")
    void register_success() throws Exception {

        // given
        RegisterRequest req = new RegisterRequest("test@test.com", "test");
        when(userService.register(req)).thenReturn(any());

        // when
        ResultActions resultActions = mockMvc.perform(post("/users/register")
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
                                        .tag("사용자")
                                        .description("사용자 등록")
                                        .requestFields(
                                                fieldWithPath("email").description("이메일"),
                                                fieldWithPath("name").description("이름")
                                        )
                                        .responseFields()
                                        .requestSchema(Schema.schema("RegisterRequest"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("사용자 등록 실패")
    void register_fail() throws Exception {

        // given
        RegisterRequest req = new RegisterRequest("testtest.com", "test");

        // when
        ResultActions resultActions = mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        // then
        resultActions
                .andExpect(status().isBadRequest());

        // rest docs
        resultActions
                .andDo(document("{class-name}/{method-name}",
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("사용자")
                                        .description("사용자 등록")
                                        .requestFields(
                                                fieldWithPath("email").description("이메일"),
                                                fieldWithPath("name").description("이름")
                                        )
                                        .requestSchema(Schema.schema("RegisterRequest"))
                                        .responseFields(
                                                fieldWithPath("status").description("HTTP 오류 코드"),
                                                fieldWithPath("message").description("오류 메시지"),
                                                fieldWithPath("detail").type(JsonFieldType.OBJECT).description("오류 상세 메시지 목록").optional(),
                                                fieldWithPath("detail.email").type(JsonFieldType.ARRAY).description("필드 오류 상세 메시지 목록").optional()
                                        )
                                        .responseSchema(Schema.schema("ErrorResponse"))
                                        .build()
                        )
                ));
    }
}