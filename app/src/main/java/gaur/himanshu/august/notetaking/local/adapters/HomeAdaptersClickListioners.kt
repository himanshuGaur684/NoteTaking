package gaur.himanshu.august.notetaking.local.adapters

import gaur.himanshu.august.notetaking.local.models.Note

interface HomeAdaptersClickListioners {


    fun clickListioners(note: Note, position:Int)

    fun detailsClickListioners(note: Note,position: Int)

}