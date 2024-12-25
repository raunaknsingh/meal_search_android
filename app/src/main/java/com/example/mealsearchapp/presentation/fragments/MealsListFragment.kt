package com.example.mealsearchapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mealsearchapp.databinding.FragmentMealsListBinding
import com.example.mealsearchapp.presentation.viewmodel.MealsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealsListFragment : Fragment() {

    private lateinit var _binding: FragmentMealsListBinding

    private val mealListViewModel: MealsListViewModel by viewModels()
    private val mealListAdapter: MealSearchAdapter = MealSearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealsListBinding.inflate(inflater, container, false)
        val view = _binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.apply {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        mealListViewModel.getMealList(it)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        mealListViewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        mealList.collect {
                            if (it.isNotEmpty()) {
                                _binding.apply {
                                    mealListRv.visibility = View.VISIBLE
                                    noDataFound.visibility = View.GONE
                                }
                                _binding.mealListRv.adapter = mealListAdapter
                                mealListAdapter.setContentList(it.toMutableList())
                                mealListAdapter.setItemClickListener {
                                    findNavController().navigate(
                                        MealsListFragmentDirections.actionMealsListFragmentToMealDetailsFragment(it.id)
                                    )
                                }
                            } else {
                                _binding.apply {
                                    mealListRv.visibility = View.GONE
                                    noDataFound.visibility = View.VISIBLE
                                }
                            }
                        }
                    }

                    launch {
                        isLoading.collect {
                            _binding.apply {
                                mealListRv.visibility = View.GONE
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
                                mealListRv.visibility = View.GONE
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