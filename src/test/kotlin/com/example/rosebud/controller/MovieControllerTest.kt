package com.example.rosebud.controller


import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import javax.transaction.Transactional


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MovieControllerTest(@Autowired val mockMvc: MockMvc, @Autowired val objectMapper: ObjectMapper) {

    @Test
    fun test001() {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/movie/"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("$.length()").value("4"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
    }

    @Test
    fun test002_when_a_movie_title_does_not_exist_it_returns_an_empty_body() {
        val notExistingMovieTitle = "titulo"
        mockMvc.perform(MockMvcRequestBuilders
                .get("/movie/searchByTitle/$notExistingMovieTitle"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("$.length()").value("0"))
    }

    @Test
    fun test003_when_the_movie_title_exist_it_returns_a_list_of_it() {
        val existingMovieTitle = "Volver al futuro"
        mockMvc.perform(MockMvcRequestBuilders
                .get("/movie/searchByTitle/$existingMovieTitle"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("$.length()").value("1"))
                .andExpect(jsonPath("$[0].director").value("Robert zemeckis"))
    }

    @Test
    fun test004_if_the_user_does_not_exist_it_returns_empty_stats() {
        val notExistingUser = "user_not_existing_test"
        mockMvc.perform(MockMvcRequestBuilders
                .get("/movie/statsForUser/$notExistingUser"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("$.listOfDirectors.length()").value("0"))
                .andExpect(jsonPath("$.hoursWatched").value("0"))
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun test005_to_generate_the_stats_the_user_need_to_add_to_the_wachted_list_first() {
        val existingMovie = "TITLE TEST"
        val existingUser = "usuario"
        val wachtedListWrapper  = WachtedListWrapper(existingUser, existingMovie)

        mockMvc.perform(MockMvcRequestBuilders
                .post("/movie/addToWachtedList/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(wachtedListWrapper)))
                .andDo(MockMvcResultHandlers.print())

        mockMvc.perform(MockMvcRequestBuilders
                .get("/movie/statsForUser/$existingUser"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("$.listOfDirectors[0][0]").value("TEST"))
                //.andExpect(jsonPath("$.hoursWatched").value("0"))
                .andDo(MockMvcResultHandlers.print())

    }
}