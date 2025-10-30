import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query'
import {getPiggyBank, getPiggyBanks, updatePiggyBank} from "../services/dataService.ts"
import type {PiggyBank} from "../model/piggyBank.ts"


export function usePiggyBanks() {
    const {isLoading, isError, data: piggyBanks} = useQuery({
        queryKey: ['piggyBanks'],
        refetchOnMount: 'always',
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