import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query'
import {addDonation, getPiggyBank, getPiggyBanks, updatePiggyBank} from "../services/dataService.ts"
import type {PiggyBank} from "../model/piggyBank.ts"
import type {NewDonation} from "../model/donation.ts"


export function usePiggyBanks() {
    const {isLoading, isError, data: piggyBanks} = useQuery({
        queryKey: ['piggyBanks'],
        queryFn: () => getPiggyBanks()
    })

    return {
        isLoading,
        isError,
        piggyBanks,
    }
}

export function usePiggyBank(id: string) {
    const {isLoading, isError, data: piggyBank} = useQuery({
        queryKey: ['piggyBank', id],
        queryFn: () => getPiggyBank(id),
    })
    return {
        isLoading,
        isError,
        piggyBank,
    }
}

export function useUpdatePiggyBank() {
    const queryClient = useQueryClient()

    const {mutate: updatePiggyBankMutation, isPending, isError, error} = useMutation({
        mutationFn: (piggyBank: PiggyBank) => updatePiggyBank(piggyBank),
        onSuccess: async (piggyBank: PiggyBank) => {
            await queryClient.invalidateQueries({queryKey: ["piggyBanks"]})
            await queryClient.invalidateQueries({queryKey: ["piggyBank", piggyBank.id]})
        }
    })
    return {updatePiggyBankMutation, isPending, isError, error}
}

export function useAddDonation(piggybankId: string) {
    const queryClient = useQueryClient()
    const {
        mutate,
        isPending,
        isError,
    } = useMutation(
        {
            mutationFn: (newDonation: NewDonation) => {
                return addDonation(newDonation)
            },
            onSuccess: () => queryClient.invalidateQueries({queryKey: ['piggyBank', piggybankId]}),
        })

    return {
        isPending,
        isError,
        addNewDonation: mutate,
    }
}