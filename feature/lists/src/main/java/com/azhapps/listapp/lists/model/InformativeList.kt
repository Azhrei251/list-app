package com.azhapps.listapp.lists.model

import com.azhapps.listapp.account.SelectedAccount

data class InformativeList(
    val id: Int,
    var name: String,
    val date: String,
    var items: MutableList<ListItem>,
    val category: Category?,
    val owner: String,
    val group: Group?
) {

    override fun equals(other: Any?) = (other as? InformativeList)?.let { otherList ->
        otherList.id == id && otherList.name == name && otherList.date == date && otherList.items.size == items.size && otherList.items.all { toFind ->
            val found = items.firstOrNull {
                toFind.id == it.id
            }
            found != null && found == toFind
        }
    } ?: false

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + items.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + group.hashCode()
        return result
    }
}

fun InformativeList.isOwnedBySelf() = this.owner == SelectedAccount.currentAccountName

