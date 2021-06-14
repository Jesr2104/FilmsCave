package com.justjump.filmscave.userArea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.R
import com.justjump.filmscave._utils.changeColorStatusBar
import com.justjump.filmscave.databinding.FragmentFriendsBinding
import com.justjump.filmscave.userArea._utils.FriendsAdapter
import com.justjump.filmscave.userArea.viewmodel.FriendsViewModel

class FriendsFragment : Fragment() {

    private lateinit var binding: FragmentFriendsBinding
    private lateinit var navController: NavController
    private lateinit var friendsViewModel: FriendsViewModel
    private var numero = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentFriendsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeColorStatusBar(requireActivity(), R.color.main_light_blue)

        friendsViewModel = ViewModelProvider(this).get(FriendsViewModel::class.java)
        navController = view.findNavController()

        // get friends and load it on the adapter
        binding.recyclerviewFriendList.adapter = FriendsAdapter(friendsViewModel.getFriends(requireContext()))

        // check for friends request
        friendsViewModel.getNumFriendsRequest()
        val myObserver = Observer<Int> {loadNotificationFriendsRequestNumber(friendsViewModel.numFriendsRequest.value!!.toInt())}
        friendsViewModel.numFriendsRequest.observe(viewLifecycleOwner, myObserver)

        binding.menuFriends.iconMenuNotifications.setOnClickListener {
            showPopupMenu(it)
        }

        // insert new friend
        binding.insertNewUser.setOnClickListener {
            navController.navigate(R.id.action_friendsFragment_to_insertNewFriendFragment)
        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    fun loadNotificationFriendsRequestNumber(friendsRequestNumber: Int){
        when(friendsRequestNumber){
            0 ->{  binding.menuFriends.notification.visibility = View.INVISIBLE }
            1,2,3,4,5,6,7,8,9 -> {
                binding.menuFriends.notification.visibility = View.VISIBLE
                binding.menuFriends.numberNotification.text = friendsRequestNumber.toString() }
            else -> {
                binding.menuFriends.notification.visibility = View.VISIBLE
                binding.menuFriends.numberNotification.text = "+" }
        }
    }

    // show POPUP menu for: (Sent, Received and Blocked users)
    private fun showPopupMenu(view: View){
        val popupMenu = PopupMenu(requireContext(),view)
        popupMenu.inflate(R.menu.friends_menu)
        popupMenu.setOnMenuItemClickListener{
            when(it.title) {
                "Blocked users" -> { navController.navigate(R.id.action_friendsFragment_to_blockedUsersFragment) }
                "Friend requests" -> { navController.navigate(R.id.action_friendsFragment_to_friendRequestsFragment) }
            }
            false
        }
        popupMenu.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        changeColorStatusBar(requireActivity(),R.color.main_dark_status_bar_color)
    }
}