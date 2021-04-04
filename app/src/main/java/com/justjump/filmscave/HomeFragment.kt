package com.justjump.filmscave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.justjump.filmscave.data.datasources.users.local.UsersLocalDataSource
import com.justjump.filmscave.databinding.FragmentHomeBinding
import com.justjump.filmscave.framework.room.users.RoomDataSource

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        val data = UsersLocalDataSource(RoomDataSource()).getUser(requireContext())

        if (data != null){
            binding.username.text = data.userName
            binding.email.text = data.email
        }

        binding.materialButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_mainActivity)
            UsersLocalDataSource(RoomDataSource()).signOut(requireContext())
        }

        // funcion un poco extraña pero funciona para que cuando este logueado y le des para atras no se vuelva a la ventana si no que se cierre la app
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            requireActivity().finish()
        }
    }
}