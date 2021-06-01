package com.justjump.filmscave.userArea

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.R
import com.justjump.filmscave.databinding.FragmentInviteFriendBinding
import com.justjump.filmscave.userArea.viewmodel.InviteFriendViewModel

class InviteFriendFragment : Fragment(), InviteFriendViewModel.Message {

    private lateinit var inviteFriendViewModel: InviteFriendViewModel
    private lateinit var binding: FragmentInviteFriendBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        binding = FragmentInviteFriendBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initial the viewModel
        inviteFriendViewModel = ViewModelProvider(this).get(InviteFriendViewModel::class.java)
        navController = view.findNavController()

        binding.inviteFriend.setOnClickListener{
            if (binding.dataUserName.text!!.isNotEmpty()){
                val thisUser = inviteFriendViewModel.getMyUsername(requireContext())
                if (thisUser.Username != binding.dataUserName.text.toString().trim()) {
                    inviteFriendViewModel.userNameValue.value = binding.dataUserName.text.toString().trim()
                    inviteFriendViewModel.inviteFriend(this, thisUser)
                } else {
                    Toast.makeText(requireContext(), getString(R.string.invite_yousetf), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.username_empty), Toast.LENGTH_SHORT).show()
            }
        }

        //********************************************************//
        //          Event to go back
        //********************************************************//
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showMessage(message: Int, success: Boolean, fieldError: Int) {
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
        if (success){ navController.navigate(R.id.action_inviteFriendFragment_to_friendsFragment) }
    }
}