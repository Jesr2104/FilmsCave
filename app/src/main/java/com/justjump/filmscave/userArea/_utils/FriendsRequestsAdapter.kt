package com.justjump.filmscave.userArea._utils

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justjump.filmscave.databinding.ItemFriendRequestListBinding
import com.justjump.filmscave.domain.users.FriendDataModel

class FriendsRequestsAdapter(private val friends: ArrayList<FriendDataModel>, private var requestInterfaceListener: RequestClickListener): RecyclerView.Adapter<FriendsRequestsAdapter.ViewHolder>() {

    interface RequestClickListener{
        fun confirmRequest(itemPosition: Int)
        fun removeRequest(itemPosition: Int)
    }

    override fun getItemCount() = friends.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemFriendRequestListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(friends[position],requestInterfaceListener) }

    class ViewHolder(private val binding: ItemFriendRequestListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(friends: FriendDataModel, requestInterfaceListener: RequestClickListener){
            binding.emailFriend.text = friends.Email
            binding.usernameFriend.text = friends.Username
            binding.dateRequest.text = friends.date

            binding.buttonConfirm.setOnClickListener {
                Log.e("jesr2104","$adapterPosition")
                requestInterfaceListener.confirmRequest(adapterPosition)
            }

            binding.buttonRemove.setOnClickListener {
                requestInterfaceListener.removeRequest(adapterPosition)


            }
        }
    }
}