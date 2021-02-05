package gaur.himanshu.august.notetaking.local.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class Note(
    var title: String,
    var desc: String,
    @PrimaryKey(autoGenerate = false)
    var time: String,
    var firestoreId:String?=null,
    val email:String?=null
):Parcelable