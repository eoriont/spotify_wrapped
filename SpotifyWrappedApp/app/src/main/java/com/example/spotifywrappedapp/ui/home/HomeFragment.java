package com.example.spotifywrappedapp.ui.home;

import android.os.Bundle;
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

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();

    //        final TextView textView = binding.textHome;
//        homeViewModel.getText()
//                .observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.lvItems;

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                summaries);

        listView.setAdapter(adapter);

        mViewModel.getSummaries().observe(getViewLifecycleOwner(), newList -> {
            summaries = newList;
            adapter.clear();
            adapter.addAll(summaries);
            adapter.notifyDataSetChanged();
        });


//        // Setup UI components
//        EditText enterFriend = binding.enterFriend;
//
//        // Set click listener to update username in ViewModel
//        binding.btnNew.setOnClickListener(v -> {
//            String username = enterFriend.getText().toString();
//            mViewModel.addFriend(username);
//        });
//
        listView.setOnItemClickListener(this::onItemDelete);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean onItemDelete(
            AdapterView<?> adapterView, View item, int pos, long id) {
        History h = summaries.get(pos);

        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(HomeFragmentDirections
                        .actionHomeFragmentToTrackFragment()
                        .setHistory(h));
        return true;
    }
}
