package com.example.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import com.example.dotify.databinding.ActivitySongListBinding
import com.example.dotify.databinding.ItemSongBinding

class SongListAdapter(private var listofSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongsViewHolder>() {

    var onSongClickListener: (song: Song) -> Unit = {_ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        return SongsViewHolder(binding)
    }

    override fun getItemCount(): Int = listofSongs.size

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val song = listofSongs[position]
        val songName = song.title
        val artistName = song.artist
        val imageID = song.smallImageID

        with(holder.binding) {
            tvTitle.text = songName
            tvArtist.text = artistName
            ivCoverPic.setImageResource(imageID)
            itemRoot.setOnClickListener {
                onSongClickListener(song)
            }
        }
    }

    fun updateSongs(newListofSongs: List<Song>) {
        this.listofSongs = newListofSongs

        notifyDataSetChanged()
    }

    class SongsViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)
}