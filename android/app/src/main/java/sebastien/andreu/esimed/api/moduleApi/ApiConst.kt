package sebastien.andreu.esimed.api.moduleApi

const val SEND_TYPE_JSON = "Accept: application/json"
const val SEND_TYPE_CONTENT = "Content-Type: multipart/form-data"

const val SIGNUP = "/signup"
const val LOGIN = "/login"

const val TIER_LIST = "/api/list"
const val GET_TIER_LIST = "/api/list/user/{userId}"
const val RANK = "/api/rank"
const val GET_RANK_LIST_ID = "/api/rank/list/{listId}"
const val GET_PICTURE_BY_TAG = "/api/picture/tag/{tag}"
const val GET_PICTURE_BY_RANK = "/api/picture/rank/{rankId}"
const val DOWNLOAD_PICTURE = "/api/picture/download/{pictureId}"
const val UPLOAD_PICTURE = "/api/picture/upload"
const val UPLOAD_PICTURE_2 = "/api/picture/uploadPicture"
