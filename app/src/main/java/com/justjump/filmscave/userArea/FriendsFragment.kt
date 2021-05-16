package com.justjump.filmscave.userArea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        // show POPUP menu for: (Sent, Received and Blocked users)
        fun showPopupMenu(view: View){
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

        binding.menuFriends.setOnClickListener {
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

    override fun onDestroy() {
        super.onDestroy()
        changeColorStatusBar(requireActivity(),R.color.main_dark_status_bar_color)
    }
}