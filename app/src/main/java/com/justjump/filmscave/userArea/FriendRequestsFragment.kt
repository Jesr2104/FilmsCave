package com.justjump.filmscave.userArea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.R
import com.justjump.filmscave.databinding.FragmentFriendRequestsBinding
import com.justjump.filmscave.domain.users.FriendDataModel
import com.justjump.filmscave.userArea._utils.FriendsRequestsAdapter
import com.justjump.filmscave.userArea.viewmodel.FriendsRequestsViewModel

class FriendRequestsFragment : Fragment(), FriendsRequestsAdapter.RequestClickListener {

    private lateinit var binding: FragmentFriendRequestsBinding
    private lateinit var navController: NavController
    private lateinit var friendsRequestViewModel: FriendsRequestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentFriendRequestsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friendsRequestViewModel = ViewModelProvider(this).get(FriendsRequestsViewModel::class.java)
        navController = view.findNavController()

        val myObserver = Observer<ArrayList<FriendDataModel>> {
            // get the friends requests and load it on adapter
            binding.recyclerviewFriendRequestsList.adapter = FriendsRequestsAdapter(it, this)
        }

        friendsRequestViewModel.getFriendsRequest(requireContext())
        friendsRequestViewModel.friendsRequestList.observe(viewLifecycleOwner, myObserver)

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun confirmRequest(itemPosition: Int) {
        Toast.makeText(requireContext(), getString(R.string.confirm_request), Toast.LENGTH_SHORT).show()
        binding.recyclerviewFriendRequestsList.adapter!!.notifyItemRemoved(itemPosition)
        // mission the part to confirm the request on the server
        //  1. add the friend en both users to do a connect both users
    }

    override fun removeRequest(itemPosition: Int, email: String) {
        val checkRemove = Observer<Boolean> {
            if (it) {
                Toast.makeText(requireContext(), getString(R.string.remove_request), Toast.LENGTH_SHORT).show()
                binding.recyclerviewFriendRequestsList.adapter!!.notifyItemRemoved(itemPosition)
            } else {
                Toast.makeText(requireContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
        }
        friendsRequestViewModel.removeFriendRequest(requireContext(), email)
        friendsRequestViewModel.checkForRemove.observe(viewLifecycleOwner, checkRemove)
    }
}