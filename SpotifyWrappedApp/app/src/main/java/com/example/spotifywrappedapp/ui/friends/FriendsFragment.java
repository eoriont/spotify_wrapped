package com.example.spotifywrappedapp.ui.friends;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.spotifywrappedapp.R;
import com.example.spotifywrappedapp.databinding.FragmentFriendsBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private FriendsViewModel mViewModel;
    private FragmentFriendsBinding binding;
    private List<String> friends = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize ViewModel
        mViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        // Inflate the layout for this fragment using View Binding
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.lvItems;

        // Initialize the adapter with the current array (empty at this point)
        adapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.listview_item,
                                    friends);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);


        // Observe the LiveData from the ViewModel
        mViewModel.getFriends().observe(getViewLifecycleOwner(), newList -> {
            // When data in LiveData changes, update the array
            friends = newList;

            // Notify the adapter of the data change
            adapter.clear();
            adapter.addAll(friends);
            adapter.notifyDataSetChanged();
        });


        // Setup UI components
        EditText enterFriend = binding.enterFriend;

        // Set click listener to update username in ViewModel
        binding.btnNew.setOnClickListener(v -> {
            String username = enterFriend.getText().toString();
            mViewModel.addFriend(username);
        });

        listView.setOnItemLongClickListener(this::onItemDelete);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding when the view is destroyed
    }

    public boolean onItemDelete(
            AdapterView<?> adapterView, View item, int pos, long id) {
        mViewModel.deleteFriend(pos);
        return true;
    }

}
