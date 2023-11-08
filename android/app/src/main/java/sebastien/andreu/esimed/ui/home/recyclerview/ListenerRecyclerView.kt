package sebastien.andreu.esimed.ui.home.recyclerview

import sebastien.andreu.esimed.model.TierList

interface ListenerRecyclerView {
    fun onUpdate(position: Int, client: TierList)
    fun onClick(position: Int, client: TierList)
}
