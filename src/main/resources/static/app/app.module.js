/*
    Defines main Angular module
 */

var AppModule = ng.core.NgModule({
    imports: [ng.platformBrowser.BrowserModule, ng.http.HttpModule],
    declarations: [AppComponent],
    bootstrap: [AppComponent]
}).Class({
    constructor: function () {
    }
});