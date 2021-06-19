package com.justjump.filmscave.userArea._utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justjump.filmscave.databinding.ItemFriendRequestListBinding
import com.justjump.filmscave.domain.users.FriendDataModel

class FriendsRequestsAdapter(private val friends: ArrayList<FriendDataModel>): RecyclerView.Adapter<FriendsRequestsAdapter.ViewHolder>() {

    override fun getItemCount() = friends.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemFriendRequestListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(friends[position]) }

    class ViewHolder(private val binding: ItemFriendRequestListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(friends: FriendDataModel){
            binding.emailFriend.text = friends.Email
            binding.usernameFriend.text = friends.Username
            binding.dateRequest.text = friends.date
        }
    }
}