package com.antyzero.cardcheck.card.mpk


enum class Type(val typeId: Long) {

    /* Default type */
    KKM(0),

    /* Types for student college cards */
    WZSIB(20),
    AGH(21), UJ(22), PK(23), UE(24), UR(25), PWST(26), AM(27), WSE(28), AIK(29), UP(30), WSH(31), KA(32), WSEI(33), IFJ_PAN(34), IF_PAN(35), IKIFP_PAN(36)
    ;

    override fun toString(): String {
        return name.replace("_", " ")
    }

    companion object {

        fun findByTypeId(typeId: Number): Type {
            val searchById = typeId.toLong()
            values().forEach {
                if (it.typeId == searchById) {
                    return it
                }
            }
            throw IllegalArgumentException("No enum value for type ID: $typeId")
        }
    }
}