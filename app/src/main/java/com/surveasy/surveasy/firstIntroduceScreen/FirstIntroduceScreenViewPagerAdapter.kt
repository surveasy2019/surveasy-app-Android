package com.surveasy.surveasy.firstIntroduceScreen

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.surveasy.surveasy.R
import com.surveasy.surveasy.login.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.surveasy.surveasy.userRoom.MIGRATION_1_2
import com.surveasy.surveasy.userRoom.UserDatabase
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstIntroduceScreenViewPagerAdapter(context: Context, view_parent: View, firstIntroduceScreen : FirstIntroduceScreen)
    : RecyclerView.Adapter<FirstIntroduceScreenViewPagerAdapter.PagerViewHolder>() {

    val db = Firebase.firestore
    var token = ""
    val context = context
    val view_parent = view_parent
    val indicator = view_parent.findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator)

    val imgList = firstIntroduceScreen.imgList
    val titleList = firstIntroduceScreen.titleList
    val contentList = firstIntroduceScreen.contentList
    private lateinit var userDB : UserDatabase


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstIntroduceScreenViewPagerAdapter.PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_firstintroducescreen, parent, false)

        return PagerViewHolder(view)
    }





    override fun onBindViewHolder(holder: FirstIntroduceScreenViewPagerAdapter.PagerViewHolder, position: Int) {
        holder.img.setImageResource(imgList[position])
        holder.title.text = titleList[position]
        holder.content.text = contentList[position]
        userDB = Room.databaseBuilder(
            context,
            UserDatabase::class.java, "UserDatabase"
        ).addMigrations(MIGRATION_1_2).allowMainThreadQueries().build()

        /*
        CoroutineScope(Dispatchers.Main).launch {
            val bool : Boolean? = userDB.userDao().getFS()

        }

         */


        if(position == imgList.size-1) {
            //indicator.visibility = View.INVISIBLE

            holder.startBtn.visibility = View.VISIBLE
            holder.lastTitle.visibility = View.VISIBLE

            /*holder.startBtn.setOnClickListener {
                userDB.userDao().updateShowFS(Firebase.auth.currentUser!!.uid, true)
            }*/

            holder.startBtn.setOnClickListener{
                FirebaseMessaging.getInstance().token.addOnCompleteListener(
                    OnCompleteListener { task ->
                        val fcmVal = task.result
                        val value = hashMapOf("fcm" to fcmVal)
                        db.collection("AndroidFirstScreen").document(fcmVal)
                            .set(value).addOnSuccessListener { Log.d(TAG,"#####save success") }

                    })

                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)

            }
        }

    }




    override fun getItemCount(): Int {
        return imgList.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.FirstIntroduceScreen_ImageView)
        val title = itemView.findViewById<TextView>(R.id.FirstIntroduceScreen_Title)
        val content = itemView.findViewById<TextView>(R.id.FirstIntroduceScreen_Content)
        val lastTitle = itemView.findViewById<TextView>(R.id.FirstIntroduceScreen_Title_last)

        val startBtn = itemView.findViewById<AppCompatButton>(R.id.FirstIntroduceScreen_StartBtn)

    }
}