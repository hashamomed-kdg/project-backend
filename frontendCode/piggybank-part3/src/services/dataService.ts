import axios from "axios"
import type {PiggyBank} from "../model/piggyBank.ts"
import type {Donation, NewDonation} from "../model/donation.ts"

export async function getPiggyBanks() {
    console.log("getPiggyBanks")
    const {data: piggyBanks} = await axios.get<PiggyBank[]>(`/piggybanks`)
    return piggyBanks
}

export async function getPiggyBank(id: string) {
    const {data: piggyBank} = await axios.get<PiggyBank>(`/piggybanks/${id}?_embed=donations`)
    return piggyBank
}

export async function updatePiggyBank(piggyBank: PiggyBank) {
    // strip donations before sending to server
    // diable the warning that donations var is unused, we use the destructuring syntax only to get piggyBankWithoutDonations
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const {donations, ...piggyBankWithoutDonations} = piggyBank

    const {data: updatedPiggyBank} = await axios.put<PiggyBank>(`/piggybanks/${piggyBank.id}`, piggyBankWithoutDonations)
    return updatedPiggyBank
}

export async function addDonation(donation: NewDonation) {
    const {data: newDonation} = await axios.post<Donation>('/donations', donation)
    return newDonation
}

