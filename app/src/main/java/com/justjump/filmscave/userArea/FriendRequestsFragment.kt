package com.justjump.filmscave.userArea

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.databinding.FragmentFriendRequestsBinding
import com.justjump.filmscave.domain.users.FriendDataModel
import com.justjump.filmscave.userArea._utils.FriendsRequestsAdapter
import com.justjump.filmscave.userArea.viewmodel.FriendsRequestsViewModel

class FriendRequestsFragment : Fragment() {

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
            binding.recyclerviewFriendRequestsList.adapter = FriendsRequestsAdapter(it)
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
}