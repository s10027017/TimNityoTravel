package com.example.tim.nityo.travel.view.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.tim.nityo.travel.R
import com.example.tim.nityo.travel.base.BaseActivity
import com.example.tim.nityo.travel.base.BaseFragment
import com.example.tim.nityo.travel.data.model.AttractionsDataItem
import com.example.tim.nityo.travel.data.model.Image
import com.example.tim.nityo.travel.databinding.FragmentIndexPageBinding
import com.example.tim.nityo.travel.utils.showError
import com.example.tim.nityo.travel.viewModel.IndexPageViewModel
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import java.io.Serializable

private const val ARG_IS_DATA = "is_data"
private const val FRAGMENT_TAG_WEBVIEW_DIALOG = "FRAGMENT_TAG_WEBVIEW_DIALOG"

class IndexPageFragment : BaseFragment() {

    private var _binding: FragmentIndexPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<IndexPageViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIndexPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.indexPageNavBar.isIndex = false
        viewModel.getAttractions(arguments?.getSerializable(ARG_IS_DATA) as AttractionsDataItem)

        bindViewModel()
        handleViewActions()
    }

    private fun handleViewActions() {
        binding.apply {
            indexPageNavBar.navBarBack.setOnClickListener {
                onBackPressed()
            }

            clWebview.setOnClickListener {
                viewModel.attraction.value?.url?.let { it1 -> goWebViewUrl(it1) }
            }

        }
    }

    private fun goWebViewUrl(url: String){
        childFragmentManager.let { fragmentManager ->
            val courseInformationWebViewFragment = WebviewDialogFragment(url)
            courseInformationWebViewFragment.show(fragmentManager, FRAGMENT_TAG_WEBVIEW_DIALOG)
        }
    }

    private fun bindViewModel() {
        //error
        viewModel.showNetworkError.observe(viewLifecycleOwner) {
            activity?.showError(BaseActivity.ErrorDisplay.Toast)
        }
//        viewModel.showServerError.observe(viewLifecycleOwner) {
//            activity?.showError(BaseActivity.ErrorDisplay.Toast)
//        }

        viewModel.apply {
            attraction.observe(viewLifecycleOwner){data ->
                binding.model = data
                binding.indexPageNavBar.title = data.name
                if(data.images.isNotEmpty()){
                    binding.banner.apply {
                        setAdapter(object : BannerImageAdapter<Image>(viewModel.attraction.value?.images){
                            override fun onBindView(
                                holder: BannerImageHolder?,
                                data: Image?,
                                position: Int,
                                size: Int
                            ) {
                                holder?.imageView?.let {
                                    Glide.with(requireActivity())
                                        .load(data?.src)
                                        .error(requireActivity().getDrawable(R.drawable.baseline_android_24))
                                        .into(it)
                                }
                            }
                        })
                        isAutoLoop(true)
                        indicator = CircleIndicator(requireContext())
                        scrollBarFadeDuration = 1000
                        start()
                    }
                }else{
                    val list = ArrayList<Drawable>()
                    requireActivity().getDrawable(R.drawable.baseline_android_24)
                        ?.let { list.add(it) }
                    binding.banner.apply {
                        setAdapter(object : BannerImageAdapter<Drawable>(list){
                            override fun onBindView(
                                holder: BannerImageHolder?,
                                data: Drawable?,
                                position: Int,
                                size: Int
                            ) {
                                holder?.imageView?.let {
                                    Glide.with(requireActivity())
                                        .load(data)
                                        .error(requireActivity().getDrawable(R.drawable.baseline_android_24))
                                        .into(it)
                                }
                            }
                        })
                        isAutoLoop(true)
                        indicator = CircleIndicator(requireContext())
                        scrollBarFadeDuration = 1000
                        start()
                    }
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        parentFragmentManager.popBackStack()
        return true
    }

    companion object {
        fun newInstance(data: AttractionsDataItem) = IndexPageFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_IS_DATA, data as Serializable)
            }
        }
    }

}