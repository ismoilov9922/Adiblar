package uz.pdp.adiblar.models

import java.io.Serializable

class AdibData : Serializable {
    private var id: String? = null
    private var fullName: String? = null
    private var dateOfBirth: String? = null
    private var category: String? = null
    private var imgUrl: String? = null
    private var description: String? = null
    private var saved: Boolean? = false


    constructor()
    constructor(
        id: String?,
        fullName: String?,
        dateOfBirth: String?,
        category: String?,
        imgUrl: String?,
        description: String?,
        saved: Boolean?,
    ) {
        this.id = id
        this.fullName = fullName
        this.dateOfBirth = dateOfBirth
        this.category = category
        this.imgUrl = imgUrl
        this.description = description
        this.saved = saved
    }

    fun getId(): String? {
        return id
    }

    fun getFullName(): String? {
        return fullName
    }

    fun getDateOfBirth(): String? {
        return dateOfBirth
    }

    fun getCategory(): String? {
        return category
    }

    fun getImgUrl(): String? {
        return imgUrl
    }

    fun getDescription(): String? {
        return description
    }

    fun getSaved(): Boolean? {
        return saved
    }

    fun setSaved(save: Boolean) {
        this.saved = save
    }


}