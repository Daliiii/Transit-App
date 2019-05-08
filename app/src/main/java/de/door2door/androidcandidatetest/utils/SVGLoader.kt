package de.door2door.androidcandidatetest.utils

import android.content.Context
import android.widget.ImageView
import com.pixplicity.sharp.Sharp
import de.door2door.androidcandidatetest.R
import okhttp3.*
import java.io.IOException
import java.util.*


object SVGLoader {
    private var httpClient: OkHttpClient? = null

    fun fetchSvg(context: Context, url: String, target: ImageView) {
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, 5 * 1024 * 1014))
                .build()
        }

        val request = Request.Builder().url(url).build()
        httpClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                target.setImageDrawable(context.getDrawable(R.drawable.icon_oepnv_heart))
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                try {
                    val stream = response.body()!!.byteStream()
                    Sharp.loadInputStream(stream).into(target)
                    stream.close()
                } catch (e : EmptyStackException){
                }
            }
        })
    }
}