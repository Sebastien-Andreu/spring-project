package sebastien.andreu.esimed.ui.list.detail.recyclerview

import sebastien.andreu.esimed.model.Rank

interface ListenerRecyclerView {
    fun onUpdate(position: Int, rank: Rank)
    fun onAddPicture(position: Int, rank: Rank)
    fun onClick(position: Int, rank: Rank)
}
