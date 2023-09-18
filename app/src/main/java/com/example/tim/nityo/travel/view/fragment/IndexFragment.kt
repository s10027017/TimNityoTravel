package com.example.tim.nityo.travel.view.fragment

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tim.nityo.travel.R
import com.example.tim.nityo.travel.base.BaseActivity
import com.example.tim.nityo.travel.data.model.AttractionsDataItem
import com.example.tim.nityo.travel.data.model.Language
import com.example.tim.nityo.travel.databinding.FragmentIndexBinding
import com.example.tim.nityo.travel.utils.CustomTextPickerDialog
import com.example.tim.nityo.travel.utils.SharedPreferencesSecret
import com.example.tim.nityo.travel.utils.loading
import com.example.tim.nityo.travel.utils.showError
import com.example.tim.nityo.travel.view.adapter.IndexAdapter
import com.example.tim.nityo.travel.viewModel.IndexViewModel
import java.util.Locale

class IndexFragment : Fragment() {

    private var _binding: FragmentIndexBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<IndexViewModel>()
    private val mAdapter by lazy {
        IndexAdapter { data ->
            goPage(data)
        }
    }
    private val mTypeDialog: CustomTextPickerDialog by lazy {
        setTypeDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIndexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAttractions()
        switchLangue()
        binding.indexNavBar.isIndex = true

        bindViewModel()
        handleViewActions()

    }

    private fun handleViewActions() {
        binding.apply {
            indexNavBar.navLanguage.setOnClickListener {
                mTypeDialog.show()
            }
            rvIndex.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        val isAddDataPosition = getNewDataIsVisible()
                        if(isAddDataPosition){
                            viewModel.page.value = viewModel.page.value!! + 1
                            viewModel.isAddList.value = true
                            viewModel.getAttractions()
                        }
                    }
                }
            })
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

        viewModel.isLoading.observe(viewLifecycleOwner) {
            activity?.loading(it)
        }

        viewModel.attractions.observe(viewLifecycleOwner){ data ->
            Log.d("attractions", "attractions")
            if(viewModel.isAddList.value == true){
                mAdapter.addList(data)
            }else{
                mAdapter.setList(data)
            }
            binding.rvIndex.adapter = mAdapter
        }

    }

    private fun goPage(data: AttractionsDataItem){
        val fragment = IndexPageFragment.newInstance(data)
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.authentication_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setTypeDialog(): CustomTextPickerDialog {
        val questionTypeArray = resources.getStringArray(R.array.language)
        val typeDialog = CustomTextPickerDialog(requireContext(), questionTypeArray) { currentIndex, chooseString ->
            when(currentIndex){
                Language.TradionnalChinese.position -> {
                    SharedPreferencesSecret.language = Language.TradionnalChinese.language
                    viewModel.getAttractions()
                    switchLangue()
                }
                Language.English.position -> {
                    SharedPreferencesSecret.language = Language.English.language
                    viewModel.getAttractions()
                    switchLangue()
                }
                Language.Japanese.position -> {
                    SharedPreferencesSecret.language = Language.Japanese.language
                    viewModel.getAttractions()
                    switchLangue()
                }
            }
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.authentication_fragment_container, IndexFragment())
                .commit()

            viewModel.page.value = 1
            viewModel.isAddList.value = false
        }

        when(SharedPreferencesSecret.language){
            Language.TradionnalChinese.language -> {
                typeDialog.setCurrentChoose(0)
            }
            Language.English.language -> {
                typeDialog.setCurrentChoose(1)
            }
            Language.Japanese.language -> {
                typeDialog.setCurrentChoose(2)
            }
        }
        return typeDialog
    }

    private fun switchLangue(){
        val resources: Resources = resources
        val config: Configuration = resources.configuration
        val dm: DisplayMetrics = resources.displayMetrics
        when(SharedPreferencesSecret.language){
            Language.TradionnalChinese.language -> {
                config.locale = Locale.TRADITIONAL_CHINESE
            }
            Language.English.language -> {
                config.locale = Locale.ENGLISH
            }
            Language.Japanese.language -> {
                config.locale = Locale.JAPANESE
            }
        }
        resources.updateConfiguration(config, dm)
    }

    private fun getNewDataIsVisible(): Boolean{
        var isVisible = false
        val layoutManager = binding.rvIndex.layoutManager
        val itemCount = layoutManager?.itemCount
        if (itemCount != null) {
            binding.rvIndex.layoutManager?.findViewByPosition(itemCount - 5)?.let {
                isVisible = getLocalVisibleRect(it, binding.rvIndex.height)
            }
        }
        return isVisible
    }

    private fun getLocalVisibleRect(view: View, offsetY: Int): Boolean{
        val p = Point()
        requireActivity().windowManager.defaultDisplay.getSize(p)
        val screenWidth: Int = p.x
        val screenHeight: Int = p.y
        val rect = Rect(0, 0, screenWidth, screenHeight)
        val location = IntArray(2)
        location[1] = (location[1] + dip2px(offsetY)).toInt()
        view.getLocationInWindow(location)
        view.tag = location[1]
        return view.getLocalVisibleRect(rect)
    }

    private fun dip2px(dpValue: Int): Float{
        val scale: Float = requireActivity().resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }

}