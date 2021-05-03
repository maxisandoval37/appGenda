package com.mxdigitalacademy.appgenda.solicitudHTTP

class HTTPResponse(quote_id: Int, quote: String, author: String, series: String) {
    var quote_id = 0
    var quote = ""
    var author = ""
    var series = ""

    init {
        this.quote_id = quote_id
        this.quote = quote
        this.author = author
        this.series = series
    }
}