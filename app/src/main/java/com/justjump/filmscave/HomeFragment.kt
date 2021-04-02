package com.justjump.filmscave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.justjump.filmscave.data.datasources.users.local.UserDataSource
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

        var data = UserDataSource(RoomDataSource()).getUser(requireContext())

        if (data != null){
            binding.username.text = data.userName
            binding.email.text = data.email
        }

        // funcion un poco extraña pero funciona para que cuando este logueado y le des para atras no se vuelva a la ventana si no que se cierre la app
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            requireActivity().finish()

        }

        return binding.root
    }


}