package com.justjump.filmscave.userArea._utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justjump.filmscave.databinding.ItemFriendRequestListBinding
import com.justjump.filmscave.domain.users.FriendDataModel

class FriendsRequestsAdapter(private val friends: ArrayList<FriendDataModel>, private var requestInterfaceListener: RequestClickListener): RecyclerView.Adapter<FriendsRequestsAdapter.ViewHolder>() {

    interface RequestClickListener{
        fun confirmRequest(itemPosition: Int, s: String)
        fun removeRequest(itemPosition: Int, email: String)
    }

    override fun getItemCount() = friends.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemFriendRequestListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(friends,position,requestInterfaceListener) }

    class ViewHolder(private val binding: ItemFriendRequestListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(friends: ArrayList<FriendDataModel>, position: Int, requestInterfaceListener: RequestClickListener){
            binding.emailFriend.text = friends[position].Email
            binding.usernameFriend.text = friends[position].Username
            binding.dateRequest.text = friends[position].date

            // event to confirm friend request
            binding.buttonConfirm.setOnClickListener {
                friends.removeAt(adapterPosition)
                requestInterfaceListener.confirmRequest(
                    adapterPosition,
                    binding.emailFriend.text as String
                )
            }

            // event to remove friend request
            binding.buttonRemove.setOnClickListener {
                friends.removeAt(adapterPosition)
                requestInterfaceListener.removeRequest(
                    adapterPosition,
                    binding.emailFriend.text as String
                )
            }
        }
    }
}