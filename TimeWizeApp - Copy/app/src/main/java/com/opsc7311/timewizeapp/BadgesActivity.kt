package com.opsc7311.timewizeapp

class BadgesActivity {


    class Badge {
        private var id: String? = null
        private var name: String? = null
        private var description: String? = null
        private var isEarned = false

        constructor()
        constructor(id: String?, name: String?, description: String?, isEarned: Boolean) {
            this.id = id
            this.name = name
            this.description = description
            this.isEarned = isEarned
        } // Getters and Setters
    }

}