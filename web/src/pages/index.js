
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
/**
 * Logic needed for the view playlist page of the website.
 */
class Index extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("index constructor");
    }


    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        this.header.addHeaderToPage();
    }
}
    /**
     * Main method to run when the page contents have loaded.
     */
    const main = async () => {
        const index = new Index();
        index.mount();
    };

    window.addEventListener('DOMContentLoaded', main);
