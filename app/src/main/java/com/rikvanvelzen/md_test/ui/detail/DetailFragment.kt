package com.rikvanvelzen.md_test.ui.detail

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
import com.rikvanvelzen.md_test.data.Result
import com.rikvanvelzen.md_test.databinding.DetailFragmentBinding
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import com.rikvanvelzen.md_test.ui.EventObserver
import com.rikvanvelzen.md_test.ui.OverviewScreenViewModel
import com.rikvanvelzen.md_test.ui.overview.ArtObjectViewHolder.Companion.OBJECT_NUMBER
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

            // TODO add loading indicator for when api not finished loading
            if (artObjectResult is Result.Success){
                setDetailedInformationOnUi(artObjectResult.data)
            } else {
                // todo handle error scenario
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