import { Post } from "./Post"
import { User } from "./User"

export class Tag {
    public idTag: number
    public tagName: string
    public posts: Post[]
    public userTags: User[]
}