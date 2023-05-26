package com.vp.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vp.task.databinding.FragmentUserDetailBinding
import com.vp.task.model.UsersListItem
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class UserDetailFragment: Fragment() {

    private var _binding: FragmentUserDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userdetail = arguments?.getSerializable("user_list") as UsersListItem
        binding.tvName.text = "NAME : ${userdetail.name}"
        binding.tvEmail.text = "EMAIL : ${userdetail.email}"
        binding.tvAddress.text = "Address : ${userdetail.address.city},${userdetail.address.street}"
        binding.btnPost.setOnClickListener {
            val bundle = Bundle().apply {
                putString("user_id", userdetail.id.toString())
            }
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                bundle
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}