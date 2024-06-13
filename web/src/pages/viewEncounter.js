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
        this.bindClassMethods(['clientLoaded', 'mount', 'spellMonster', 'addEncounterToPage', 'createNewEncounterSubmit', 'addMonsterTargetsToPage', 'attackMonster'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addEncounterToPage);
        this.dataStore.addChangeListener(this.addMonsterTargetsToPage);
        //this.dataStore.addChangeListener(this.createNewEncounterSubmit);
        this.header = new Header(this.dataStore);
        console.log("viewEncounter constructor");
    }

    /**
     * Once the client is loaded, get the encounter.
     */
    async clientLoaded() {
        const encounter = await this.client.getEncounter();
        this.dataStore.set('encounter', encounter);
        this.dataStore.set("monsterList", encounter.monsterList);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        document.getElementById('createEncounter').addEventListener('click', this.createNewEncounterSubmit);
        document.getElementById('swordAttack').addEventListener('click', this.attackMonster);
        document.getElementById('spellAttack').addEventListener('click', this.spellMonster);
        this.client = new TaskWarriorClient();
        this.clientLoaded();
    }

    addMonsterTargetsToPage(){
        const encounter = this.dataStore.get('encounter');
        if (encounter == null) {
            return;
        }
        let monsterList = encounter.monsterList;
        let optionHTML = `<div class="form-group">
                            <label for="exampleFormControlSelect1">Select Monster</label>
                            <select class="form-control" id="exampleFormControlSelect1">`;
        let monster;
        let counter = 1;
        for (monster of monsterList) {
            optionHTML += `<option value="${counter}">${monster.name}</option>`;
        }
        optionHTML += `</select>
                         </div>`;
        document.getElementById('monsterOptions').innerHTML = optionHTML;
    }

    /**
* When encounterr is updated in the datastore, updates user metadata on page
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
                <div class="monster">
                <div class="card">
                    <span class="name">${monster.name}</span>`;

                    if (monster.description == "easy") {
                        encounterHTML += `<span class="description">
                        ★☆☆</span>`;
                    } else if (monster.description == "medium") {
                        encounterHTML += `<span class="description">
                        ★★☆</span>`;
                    } else {
                        encounterHTML += `<span class="description">
                        ★★★</span>`;
                    }
                    encounterHTML += `
                    <span class="attackPower">Attack Power: ${monster.attackPower}</span>
                    <div class="progress">
                        <div class="progress-bar bg-danger" role="progressbar" style="width: `
                        + (monster.currentHealth / monster.startingHealth) * 100 +
                        `%" aria-valuenow="${monster.currentHealth}" aria-valuemin="0" aria-valuemax="${monster.startingHealth}"></div>
                    </div>
                    </div>
                </div>
            `;
        }
        document.getElementById('encounter-monsters').innerHTML = encounterHTML;
    }

     /**
* When user is updated in the datastore, updates user metadata on page
*/
async createNewEncounterSubmit(evt) {
    evt.preventDefault();

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const createButton = document.getElementById('createEncounter');
    const origButtonText = createButton.innerText;
    createButton.innerText = 'Loading...';

    const encounter = await this.client.createEncounter((error) => {
        createButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    });
    this.dataStore.set('encounter', encounter);
}

     /**
* Uses an attack to update the encounter.
*/
async attackMonster(evt) {
    evt.preventDefault();

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const attackButton = document.getElementById('swordAttack');
    const monsterTarget = document.getElementById('exampleFormControlSelect1').value;
    const origButtonText = attackButton.innerText;
    attackButton.innerText = 'Loading...';

    const monsterList = await this.client.attackMonster(monsterTarget, (error) => {
        attackButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    });
    this.dataStore.set('monsterList', monsterList);
    attackButton.innerText = origButtonText;
}
     /**
* Uses an attack to update the encounter.
*/
async spellMonster(evt) {
    evt.preventDefault();

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const spellButton = document.getElementById('spellAttack');
    const origButtonText = spellButton.innerText;
    spellButton.innerText = 'Loading...';

    const monsterList = await this.client.spellMonster((error) => {
        spellButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    });
    this.dataStore.set('monsterList', monsterList);
    spellButton.innerText = origButtonText;
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
