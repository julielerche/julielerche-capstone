import TaskWarriorClient from '../api/taskWarriorClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
/**
 * Logic needed for the view playlist page of the website.
 */
class ViewEncounter extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addEncounterToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addEncounterToPage);
        this.header = new Header(this.dataStore);
        console.log("viewEncounter constructor");
    }

    /**
     * Once the client is loaded, get the encounter.
     */
    async clientLoaded() {
        const encounter = await this.client.getEncounter();
        this.dataStore.set('encounter', encounter);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        this.header.addHeaderToPage();

        this.client = new TaskWarriorClient();
        this.clientLoaded();
    }

    /**
* When user is updated in the datastore, updates user metadata on page
*/
    addEncounterToPage() {
        const encounter = this.dataStore.get('encounter');
        if (encounter == null) {
            return;
        }
        let monsterList = encounter.monsterList;
        let encounterHTML = '';
        let monster;
        for (monster of monsterList) {
            encounterHTML += `
                <li class="assetMonster">
                    <span class="name">${monster.name}</span>
                    <span class="description">${monster.description}</span>
                    <span class="attackPower">${monster.attackPower}</span>
                    <span class="currentHealth">${monster.currentHealth}</span>
                    <span class="startingHealth">${monster.startingHealth}</span>
                </li>
            `;
        }
        document.getElementById('encounter-monsters').innerHTML = encounterHTML;
    }
}
    /**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewEncounter = new ViewEncounter();
    viewEncounter.mount();
};

window.addEventListener('DOMContentLoaded', main);
