package com.justjump.filmscave.userArea

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justjump.filmscave.databinding.ItemFriendListBinding
import com.justjump.filmscave.domain.users.FriendDataModel

class FriendsAdapter(private val friends: ArrayList<FriendDataModel>): RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    override fun getItemCount() = friends.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemFriendListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(friends[position]) }

    class ViewHolder(private val binding: ItemFriendListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(friends: FriendDataModel){
            binding.emailFriend.text = friends.Email
            binding.usernameFriend.text = friends.Username
        }
    }
}