package com.example.spotifywrappedapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrappedapp.databinding.FragmentHomeBinding;
import com.example.spotifywrappedapp.models.History;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel mViewModel;
    private List<History> summaries = new ArrayList<>();
    private ArrayAdapter<History> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.lvItems;

//        adapter = new ArrayAdapter<>(getActivity(),
//                R.layout.listview_item,
//                R.id.text_view,
//                summaries);
        adapter = new CustomAdapter(getActivity(), summaries,
                this::onItemClick);

        listView.setAdapter(adapter);

        mViewModel.getSummaries().observe(getViewLifecycleOwner(), newList -> {
            summaries = newList;
            adapter.clear();
            adapter.addAll(summaries);
            adapter.notifyDataSetChanged();
        });


//        listView.setOnItemClickListener(this::onItemClick);
//
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean onItemClick(
            AdapterView<?> adapterView, View item, int pos, long id) {
        History h = summaries.get(pos);
        Log.d("HISTORY", "History " + pos + " clicked!" + h.toString());

        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(HomeFragmentDirections
                        .actionHomeFragmentToTrackFragment()
                        .setHistory(h));
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.fetchSummaries();
        adapter.notifyDataSetChanged();
    }
}
