export type Donation = {
    id: string
    amount: number
    timestamp: Date
    piggybankId: string
    shortMessage: string
    longMessage: string
    sponsor: {
        id: string
        name: string
    }
}

export type NewDonation = Omit<Donation, 'id'>
export type DonationFormValues = Omit<Donation, 'id' | 'timestamp' | 'piggybankId' | 'sponsor'>

