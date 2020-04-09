package safe.com.gamehuber.mvp.model

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.net.service.FileService
import java.io.File
import java.util.*

class UploadFileModel : BaseModel() {

    suspend fun uploadFiles(fileUrls: List<String>): MyResult<List<String>> {
        var files = ArrayList<File>()
        for (fileUrl: String in fileUrls) {
            files.add(File(fileUrl))
        }
        val parts = filesToMultipartBodyParts(files, "image/png")
        return apiCall(call = {
            executeResponse(FileService.uploadFile(parts))
        }, errorMessage = "网络异常")
    }

    suspend fun uploadFileVideo(fileUrl: String): MyResult<String> {
        val part = filesToMultipartBodyPart(File(fileUrl), "")
        return apiCall(call = {
            executeResponse(FileService.uploadVideo(part))
        }, errorMessage = "网络异常")
    }

    //多文件上传
    private fun filesToMultipartBodyParts(files: List<File>, mediaType: String): List<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>(files.size)
        for (file in files) {
            val requestBody = RequestBody.create(MediaType.parse(mediaType), file)
            val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
            parts.add(part)
        }
        return parts
    }

    //单文件上传
    private fun filesToMultipartBodyPart(file: File, mediaType: String): MultipartBody.Part {
        val requestBody = RequestBody.create(MediaType.parse(mediaType), file)
        return MultipartBody.Part.createFormData("file", file.name, requestBody)
    }


}
