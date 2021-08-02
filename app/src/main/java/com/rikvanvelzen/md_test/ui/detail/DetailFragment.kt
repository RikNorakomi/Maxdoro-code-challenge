package com.rikvanvelzen.md_test.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rikvanvelzen.md_test.Injection
import com.rikvanvelzen.md_test.data.Result
import com.rikvanvelzen.md_test.databinding.DetailFragmentBinding
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import com.rikvanvelzen.md_test.ui.EventObserver
import com.rikvanvelzen.md_test.ui.RijksCollectionViewModel
import com.rikvanvelzen.md_test.ui.overview.ArtObjectViewHolder.Companion.OBJECT_NUMBER

class DetailFragment : Fragment() {

    private lateinit var viewModel: RijksCollectionViewModel
    private lateinit var objectNumber: String
    private lateinit var binding: DetailFragmentBinding

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(RijksCollectionViewModel::class.java)


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