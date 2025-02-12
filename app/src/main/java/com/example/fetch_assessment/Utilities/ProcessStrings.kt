package com.example.fetch_assessment.Utilities

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.fetch_assessment.Entities.Item
import com.example.fetch_assessment.MainActivity

class ProcessStrings {

    companion object {
        var items:MutableList<Item> = mutableListOf()
        val item_map: MutableMap<String, MutableList<Item>> = mutableMapOf()

        @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
        fun processItem(ctx: Context, res: String): MutableMap<String, MutableList<Item>>{
            val list = res.split("},")
//            println("The split list is $list")
            for (pos in list){
//                print("The pos is: ${pos.drop(1)}")
                val values = pos.split(",")
//                print(values)
//                println(values[0].split(": "))
                val id = values[0].split(": ")[1]
                val listId = (values[1].split(": "))[1]
                val name = (values[2].split(": "))[1]

                if(name == "\"\"" || name.trim() == "null" || name.replace("}","").replace("]",""
                ).trim() == "null"){
                    continue
                }

                items.add(Item(id, listId, name))
                item_map.getOrPut(listId) { mutableListOf() }.add(Item(id, listId, name))
            }

//            print("The total is: ${items.size}")
//            print("The grouped list is: $item_map")


//            val c = ctx as CustomInterface
//            c.sendData(item_map)
            return item_map
        }
    }
}