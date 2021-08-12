package com.rikvanvelzen.coding_challenge.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rikvanvelzen.coding_challenge.databinding.DetailFragmentBinding
import com.rikvanvelzen.coding_challenge.model.ArtObjectDetails
import com.rikvanvelzen.coding_challenge.utils.EventObserver
import com.rikvanvelzen.coding_challenge.ui.overview.ArtObjectViewHolder.Companion.OBJECT_NUMBER
import com.rikvanvelzen.coding_challenge.utils.Result
import com.rikvanvelzen.coding_challenge.utils.Result.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailScreenViewModel by viewModels()
    private lateinit var objectNumber: String
    private lateinit var binding: DetailFragmentBinding

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { it ->
            objectNumber = it.getString(OBJECT_NUMBER).toString()
            viewModel.loadDetailedInformation(objectNumber)
        }

        viewModel.detailInformation.observe(viewLifecycleOwner, EventObserver { artObjectResult ->

            when (artObjectResult){
                is Success -> setDetailedInformationOnUi(artObjectResult.data)
                is Error -> {
                    // inform user of error
                }
                is Loading -> {
                    // show loading indication
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setDetailedInformationOnUi(artObjectDetails: ArtObjectDetails) {
        Log.e("DetailFragment", "setDetailedInformationOnUi: $artObjectDetails")

        binding.apply {
            title.text = artObjectDetails.title
            description.text = artObjectDetails.description
            author.text = artObjectDetails.author
            physicalMedium.text = artObjectDetails.physicalMedium

            Glide.with(imageView)
                .load(artObjectDetails.imageDetail.url)
                .centerCrop()
                .into(imageView)
        }
    }
}