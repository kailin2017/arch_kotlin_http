package com.kailin.arch_kotlin_http.repo.typicode

import org.json.JSONArray
import java.io.Serializable
import java.lang.Exception

data class TypiCode(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
) : Serializable {
    companion object {

        const val tag = "TypiCode"

        fun string2TypiCodeList(source: String): MutableList<TypiCode> {
            val result: MutableList<TypiCode> = mutableListOf()
            try {
                val jsonArray = JSONArray(source)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val temp = TypiCode(
                        jsonObject.getInt("albumId"),
                        jsonObject.getInt("id"),
                        jsonObject.getString("thumbnailUrl"),
                        jsonObject.getString("title"),
                        jsonObject.getString("url")
                    )
                    result.add(temp)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                return result
            }
        }
    }
}
