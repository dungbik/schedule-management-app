package nbc.sma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@AutoConfigureRestDocs
public abstract class RestDocsTestSupport {

    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;
}
