package com.mportog.guidanceprojecttest.repository

import com.mportog.guidanceprojecttest.data.service.FlowService
import com.mportog.guidanceprojecttest.model.dto.toQuote
import com.mportog.guidanceprojecttest.model.dto.toUser
import com.mportog.guidanceprojecttest.model.ui.DataFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

internal class FlowRepository(private val flowService: FlowService) {

    suspend fun getData(withDelay: Boolean): Flow<DataFlow> = withContext(Dispatchers.IO) {
        val quote = async { getQuote() }
        val status = async { getStatus(withDelay) }
        val users = async { getUsers() }
        flow {
            DataFlow(
                quote = quote.await(),
                status = status.await(),
                users = users.await()
            )
        }
    }

    private suspend fun getQuote() =
        runCatching { flowService.getKanyeQuote().toQuote() }.getOrNull()

    private suspend fun getStatus(withDelay: Boolean) = runCatching {
        if (withDelay) {
            flowService.getOKStatusCodeWithdelay()
        } else {
            flowService.getRandomStatusCode()
        }
    }.getOrNull()

    private suspend fun getUsers() =
        runCatching { flowService.getUsersList().map { it.toUser() } }.getOrDefault(
            emptyList()
        )
}