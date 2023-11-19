package enums

enum class Status(s: String) {
    THINKING("thinking"),
    HUNGRY("hungry"),
    EATING("eating");

    companion object {


        var description: String = ""
            get() {
                return field
            }
    }


}