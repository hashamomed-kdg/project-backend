import axios from "axios"
import type {PiggyBank} from "../model/piggyBank.ts"

export async function getPiggyBanks() {
    console.log("getPiggyBanks")
    const {data: piggyBanks} = await axios.get<PiggyBank[]>(`/piggybanks`)
    return piggyBanks
}

export async function getPiggyBank(id: string) {
    const {data: piggyBank} = await axios.get<PiggyBank>(`/piggybanks/${id}`)
    return piggyBank
}

export async function updatePiggyBank(piggyBank: PiggyBank) {
    const {data: updatedPiggyBank} = await axios.put<PiggyBank>(`/piggybanks/${piggyBank.id}`, piggyBank)
    return updatedPiggyBank
}
