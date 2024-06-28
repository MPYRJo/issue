package com.yoong.myissue.infra.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.yoong.myissue.exception.clazz.ModelNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class S3Manager(
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,
    private val amazonS3: AmazonS3
){

    fun uploadImage(multipartFile: MultipartFile): String{
        val originalFilename = multipartFile.originalFilename ?: throw ModelNotFoundException("확장자", "null")
        val fileName = "${UUID.randomUUID()}-$originalFilename"
        val objectMetadata = setFileDateOption(
            type="image",
            file= getFileExtension(originalFilename),
            multipartFile = multipartFile
        )

        amazonS3.putObject(bucket, fileName, multipartFile.inputStream , objectMetadata)
        return fileName
    }

    fun getFile(fileName: String): String{
        return amazonS3.getUrl(bucket,fileName).toString()
    }

    fun deleteFile(fileName: String) {
        amazonS3.deleteObject(bucket,fileName)
    }

        private fun getFileExtension(originalFilename: String): String {
        val text = originalFilename.lastIndexOf('.')
        return originalFilename.substring(text + 1).trim()
    }

    private fun setFileDateOption(type: String, file: String, multipartFile: MultipartFile): ObjectMetadata {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = "/$type/${getFileExtension(file)}"
        objectMetadata.contentLength = multipartFile.inputStream.available().toLong()

        return objectMetadata
    }

}