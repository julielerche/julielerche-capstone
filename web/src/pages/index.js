import Authenticator from "../api/authenticator";
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
/**
 * Logic needed for the view playlist page of the website.
 */
class Index extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'redirectButtons'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.authenticator = new Authenticator();
        console.log("index constructor");
    }


    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        this.header.addHeaderToPage();
    }

    /**
 * When the user is logged in, redirect to createUser.
 */
    redirectButtons() {
        const loggedIn = this.authenticator.isUserLoggedIn;
        if (loggedIn) {
            let buttonHTML = `
            <a href="http://www.google.com/">
            <button>Visit Google</button>
            </a>`;
            document.getElementById("redirectButton").innerHTML = buttonHTML;
        }
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
