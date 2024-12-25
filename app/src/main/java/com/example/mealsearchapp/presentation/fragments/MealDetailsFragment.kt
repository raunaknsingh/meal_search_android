package com.example.mealsearchapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mealsearchapp.databinding.FragmentMealDetailsBinding
import com.example.mealsearchapp.presentation.viewmodel.MealsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailsFragment : Fragment() {

    private lateinit var _binding : FragmentMealDetailsBinding

    private val args: MealDetailsFragmentArgs by navArgs()

    private val mealsDetailViewModel: MealsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.mealid?.let {
            mealsDetailViewModel.getMealDetail(it)
        }

        setFlowCollectors()
    }

    private fun setFlowCollectors() {

        mealsDetailViewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        mealDetail.collect {
                            if (it.isNotEmpty()) {
                                val meal = it.firstOrNull()
                                _binding.apply {
                                    meal?.let { meal ->
                                        Glide.with(mealImage.context).load(meal.image).into(mealImage)
                                        mealInstructions.text = meal.instructions
                                    }
                                }
                            } else {
                                _binding.apply {
                                    noDataFound.visibility = View.VISIBLE
                                }
                            }
                        }
                    }

                    launch {
                        isLoading.collect {
                            _binding.apply {
                                progressCircular.visibility = if (it) View.VISIBLE else View.GONE
                                noDataFound.visibility = View.GONE
                            }
                        }
                    }

                    launch {
                        error.collect {
                            if (it.isBlank()) {
                                return@collect
                            }
                            _binding.apply {
                                progressCircular.visibility = View.GONE
                                noDataFound.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}